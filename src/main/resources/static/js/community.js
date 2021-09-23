/*
  提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#community_content").val();
    var type = $("#type").val();

    if (!content){
        alert("不能回复空内容")
        return
    }

    console.log(questionId);
    console.log(content);
    console.log(type);

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({question_id: questionId, content: content, type: type}),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else if (response.code == 2003){
                alert("不能回复空内容");
            }

        },
        dataType: "json"
    });

}
/*
未登录，跳转登录
 */
function login(){
    alert("未登录");
}
/*
    展开二级评论
 */
function collapseComments(e){
    var type = $("#type2").val();

    var id = e.getAttribute("data-id");
    var comments = $('#comment-'+id);
    var collapse = e.getAttribute("data-collapse")
    if (collapse){
        //折叠评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        // 展开评论
        comments.addClass("in")
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
    }

    $.ajax({
        type: "GET",
        url: "/comment/id",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({question_id: id, content: content, type: type}),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else if (response.code == 2003){
                alert("不能回复空内容");
            }

        },
        dataType: "json"
    });

}
/*
    点赞
 */
function doLike(that){

    var userId =$(that).attr('data-id');
    var questionId =$(that).attr('data-questionId');
    if (userId === 0){
        alert("请登录后再点赞")
    }
    $.ajax({
        type: "POST",
        url: "/doLike",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({userId: userId,questionId : questionId }),
        success: function (response) {
            if (response.code == 0) {
                window.location.reload();
            } else if (response.code == -1){
                alert("错误");
            }

        },
        dataType: "json"
    });
}
/*
    二级评论的回复
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({question_id: commentId, content: content, type: 2}),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else if (response.code == 2003){
                alert("不能回复空内容");
            }

        },
        dataType: "json"
    });


}
function selectTag(value){
    var previous = $("#tag").val();
    if (previous){
        $("#tag").val(previous+','+value);
    }else {
        $("#tag").val(value);
    }

}