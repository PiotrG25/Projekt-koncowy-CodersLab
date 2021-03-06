$(function(){

    var levelElement = $("#top10Level");
    var movesElement = $("#top10Moves");
    var timeElement = $("#top10Time");

    var greenRedMoves = $("#top10Moves i");
    var greenRedTime = $("#top10Time i");

    var greenRed;
    var top10Level = levelElement.data("level");

    if(greenRedMoves.length === 1){
        greenRed = "Moves";
        if(greenRedMoves.hasClass("green")){
            greenRed = "green" + greenRed;
        }else{
            greenRed = "red" + greenRed;
        }
    }else{
        greenRed = "Time";
        if(greenRedTime.hasClass("green")){
            greenRed = "green" + greenRed;
        }else{
            greenRed = "red" + greenRed;
        }
    }



    levelElement.on("click", function(){

        if(top10Level === 1 || top10Level === 2){
            top10Level++;
        }else{
            top10Level = 1;
        }

        postForm(top10Level, greenRed);
    });


    movesElement.on("click", function(){
        if(greenRed === "greenMoves"){
            postForm(top10Level, "redMoves");
        }else{
            postForm(top10Level, "greenMoves");
        }
    });

    timeElement.on("click", function(){
        if(greenRed === "greenTime"){
            postForm(top10Level, "redTime");
        }else{
            postForm(top10Level, "greenTime");
        }
    });

    function postForm(level, greenRed){
        var form = $(
            "<form method='post' style='display: none;'>" +
                "<input type='text' name='top10Level' value='" + level + "'/>" +
                "<input type='text' name='greenRed' value='" + greenRed + "'/>" +
                "<input type='submit' id='clickMe'/>" +
            "</form>"
        );

        $("body").append(form);

        var clickMe = $("#clickMe");
        clickMe.click();
    }

});
