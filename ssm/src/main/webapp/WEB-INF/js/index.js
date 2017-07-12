/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
$(function(){
    // loadUserList();
    initJsgridList();
    $("#userList").jsGrid({
        controller:{
            loadData: function(filter) {
                return $.ajax({
                    type: "GET",
                    url: "users",
                    data: filter,
                    dataType: "json"
                });
            },
            insertItem: function(item) {
                console.log(item.userId+"_insert_"+item);
                var d = $.Deferred();
                return $.ajax({
                    type: "POST",
                    url: "users",
                    data: item,
                    dataType: "json"
                }).done(function(response) {
                    d.resolve(response);
                    grid.loadData();
                }).fail(function() {
                    grid.loadData();
                    d.resolve(previousItem);
                });
            },
            updateItem: function(item) {
                console.log(item.userId+"_update_"+item);
                var d = $.Deferred();
                item._method = "PUT";
                return $.ajax({
                    type: "POST",
                    url: "users/" + item.userId,
                    data: item,
                    dataType: "json"
                }).done(function(response) {
                    console.log(response);
                    d.resolve(response);
                    grid.loadData();
                }).fail(function() {
                    grid.loadData();
                    d.resolve(previousItem);
                });
            },
            deleteItem: function(item) {
                console.log(item.userId+"_delete_"+item);
                var d = $.Deferred();
                item._method = "DELETE";
                return $.ajax({
                    type: "DELETE",
                    url: "users/" + item.userId,
                    data: item,
                    dataType: "json"
                }).done(function(response) {
                    d.resolve(response);
                    grid.loadData();
                }).fail(function() {
                    grid.loadData();
                    d.resolve(previousItem);
                });
            }
        },
        onItemInserting: function(args) {
            previousItem = args.previousItem;
            grid = args.grid;
        },

        onItemUpdating: function(args) {
            previousItem = args.previousItem;
            grid = args.grid;
        },

        onItemDeleting: function(args) {
            previousItem = args.previousItem;
            grid = args.grid;
        },

        width: "80%",
        height : "auto",

        confirmDeleting: true,
        deleteConfirm: "Are you sure delete this user?",

        inserting: true,
        editing: true,
        sorting: false,
        deleting: true,
        paging: true,
        autoload: true,

        pagerContainer: null,
        pageIndex: 1,
        pageSize: 5,
        pageButtonCount: 15,
        pagerFormat: "Pages: {first} {prev} {pages} {next} {last}    {pageIndex} of {pageCount}",
        pagePrevText: "Prev",
        pageNextText: "Next",
        pageFirstText: "First",
        pageLastText: "Last",
        pageNavigatorNextText: "...",
        pageNavigatorPrevText: "...",

        fields: [
            { name: "userId", css: "hide"},
            { name: "userName", title: "User Name",  type: "text", validate: "required", valueField: "abbreviation" },
            { name: "password", title: "Password", css: "hide", type: "text",  value: 123456 },
            { name: "userSex", title:"Sex", type:"select",items:visible, validate:"required", valueField:"name",textField:"name", width: 40},
            { name: "userMail", title: "Mail", type: "text"},
            { name: "creationDate", title: "Created On", type: "text", editing: false, inserting: false},
            { name: "lastUpdateDate", title: "Last Updated On", type: "text", editing: false, inserting: false},
            { type: "control",  editButton: true,  deleteButton: true, width:50 }
        ]
    });

});

var visible = [
    {name:"W", id:"W"},
    {name:"M", id:"M"}
];

/**
 * delete user by userId
 * @param userId
 */
function deleteUser(userId) {
    var isFlag = window.confirm("Are you sure delete this user?");
    if(isFlag) {
        $.ajax({
            type: "DELETE",
            url : "users/"+userId,
            async : true,
            cache : false,
            success : function () {
                loadUserList();
                console.log("delete success");
            }
        });
    }
}

/**
 * 读取所有user
 */
function loadUserList(){
    $.ajax({
        type: "GET",
        url : "users",
        async : true,
        cache : false,
        success : function(response){
            $("#tab-list").text("");
            console.log(response);
            var userList = response.userList;
            if(userList != null && userList.length > 0) {
                var tab_tr = '<tr style="height:50px;" id="tab-head">'
                    +'<th>No.</th>'
                    +'<th>NAME</th>'
                    +'<th>SEX</th>'
                    +'<th>MAIL</th>'
                    +'<th style="width: 120px; text-align: center;">Function</th>'
                    +'</tr>';
                for(var i = 0; i < userList.length; i++){
                    tab_tr += '<tr style="height:36px;" class = "tab-tr">'
                        +'<td>'+ userList[i].userId +'</td>'
                        +'<td>'+ userList[i].userName +'</td>'
                        +'<td>'+ userList[i].userSex +'</td>'
                        +'<td>'+ userList[i].userMail +'</td>'
                        +'<td><button onclick=updateUser('+ userList[i].userId +')>Edit</button>'
                        +'<button onclick=deleteUser('+ userList[i].userId +')>Delete</button></td>'
                        +'</tr>';
                }
                $("#tab-list").append(tab_tr);
            }
        }
    });
}

/*$("#btn-add").on("click", function(){
});*/

function addFun(){
    window.location.href = "addPage";
}

function updateUser(userId) {
    window.location.href = "updatePage?userId="+userId;
}

$(document).ready(function() {
    $("#searchBtn").click(function() {
        initJsgridList();
    });
});

function initJsgridList() {
    var searchContent = $("#searchContent").val();
    var params = {
        "userName" : searchContent,
        "userSex" : searchContent,
        "userMail" : searchContent
    };
    $("#userList").jsGrid("loadData", params);
}