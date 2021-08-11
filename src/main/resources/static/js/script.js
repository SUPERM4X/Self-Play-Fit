$(function() {

  $("#test").on("click", function() {

    var model = {
      "no" : "1",
      "name" : "1",
      "favorite" : [ {
        "use" : "movie",
        "time" : "120m"
      }, {
        "use" : "voise",
        "time" : "5m"
      } ]
    };

    console.log(model);
    console.log(JSON.stringify(model));

    $.ajax({
      url : '/jsTest',
      type : 'POST',
      data : JSON.stringify(model),
      contentType : 'application/json',
      error : function() {
        console.log("error");
      },
      success : function() {
        console.log("success");
      }
    });

  });

});