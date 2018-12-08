$(function(){

    var buttons = $("#game").find(".buttonInGame");
    var pushed = [];

    var clicks = 0;
    var gameTime = 0;


    buttons.each(function(index, element){
        pushed.push(true);
        $(element).css("background-color", "green");
    });

    buttons.each(function(index, element){

        var trs = $("tr");
        var tr = trs.eq(index);
        var tds = tr.children();
        var len = tds.length;

        $(element).on("click", function(){
            changeButton(index);
            for(var i = 0; i < len; i++){
                changeButton(parseInt(tds.eq(i).text()))
            }
        });
    });
    buttons.click();

    buttons.click(function(){
        incrementCounter();
        if(checkWinCondition()){
            redirectGame();
        }
    });

    setInterval(function(){incrementGameTime();}, 1000);

//Takie tam funkcje


    function changeButton(index){
        if(pushed[index] === true){
            buttons.eq(index).css("background-color", "red");
            pushed[index] = false;
        }else{
            buttons.eq(index).css("background-color", "green");
            pushed[index] = true;
        }
    }

    function checkWinCondition(){
        for(var i = 0; i < pushed.length; i++){
            if(pushed[i] === false){
                return false;
            }
        }
        return true;
    }

    function incrementCounter(){
        var div = $("#counter");
        clicks++;
        div.text("Kliknięcia: " + clicks);
    }

    function incrementGameTime(){
        var div = $("#timer");
        gameTime++;

        var secs = gameTime % 60;
        var minutes = (gameTime - secs) / 60;

        if(secs < 10){
            secs = "0" + secs;
        }
        if(minutes < 10){
            minutes = "0" + minutes;
        }

        div.text("Czas gry: " + minutes + ":" + secs);
    }

    function redirectGame(){
        var form = $(
            "<form action='/game' method='post' style='display: none;'>" +
                "<input type='number' name='moves' value='" + clicks + "'/>" +
                "<input type='number' name='time' value='" + gameTime + "'/>" +
                "<input type='submit' id='goToGame'/>" +
            "</form>"
        );
        $("body").append(form);

        var toClick = $("#goToGame");
        toClick.click();
    }
});

