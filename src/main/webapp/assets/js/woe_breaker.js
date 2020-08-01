$(document).ready(function() {
    /*Load a member details*/
    $.get("rest/woe/breaker/20200725", function(data) {
        console.log("break ready", data);
        castBreaker(data);
    }).always(function() {
        //complete();
    });
});

function castBreaker(breakInfo) {
    jQuery.each(breakInfo, function(i, cast) {
        $("#"+ i).append("<h2>"+ cast.map_name +"</h2>");
        // Foreach castles
        // CAST 1
        $("#"+ i).append(renderCastle(cast.castle_1, 1));

        // CAST 2
        $("#"+ i).append(renderCastle(cast.castle_2, 2));

        // CAST 3
        $("#"+ i).append(renderCastle(cast.castle_3, 3));

        // CAST 4
        $("#"+ i).append(renderCastle(cast.castle_4, 4));

        // CAST 5
        $("#"+ i).append(renderCastle(cast.castle_5, 5));
        $("#"+ i).append("<br>");
    });

    function renderCastle(inf, castNumber) {
        let castDet = $("<br><div class='castle_num'><h3>"+ inf.castle_name +" "+ castNumber +" </h3></div>");
        let breaks = $("<div class='break_timeline'><div class='timeline_color'></div></div>");
        let post0 = inf.break_history[0].timestamp_break;
        let post100 = inf.break_history[inf.break_history.length-1].timestamp_break - post0;
        jQuery.each(inf.break_history, function (k, breaks_h) {
            let postEmb = ( (breaks_h.timestamp_break-post0) * 100)/post100;
            breaks.append(`
                <div class='breaker_point' style='margin-left: `+ postEmb +`%;'>
                    <div class='breaker_guild_tl'>
                        <img src='./assets/img/ro/guilds/emblems/Poring_`+ breaks_h.guild_id +`_`+ breaks_h.guild_emblem +`.png'>
                    </div>
                </div>
            `);
        })
        castDet.append(breaks);
        return castDet;
    }
}