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
        $("#"+ i).append("<br><p class='city_name'>"+ cast.map_name +"</p>");
        // Foreach castles
        // CAST 1
        let castDet = $("<br><div class='castle_1'><p class='castle_name'>"+ cast.castle_1.castle_name +" 1 </p></div>");
        let breaks = $("<div class='break_timeline'><div class='timeline_color'></div></div>");
        let post0 = cast.castle_1.break_history[0].timestamp_break;
        let post100 = cast.castle_1.break_history[cast.castle_1.break_history.length-1].timestamp_break - post0;
        jQuery.each(cast.castle_1.break_history, function (k, breaks_h) {
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
        $("#"+ i).append(castDet);

        // CAST 2
        castDet = $("<br><div class='castle_1'><p class='castle_name'>"+ cast.castle_2.castle_name +" 2 </p></div>");
        breaks = $("<div class='break_timeline'><div class='timeline_color'></div></div>");
        post0 = cast.castle_2.break_history[0].timestamp_break;
        post100 = cast.castle_2.break_history[cast.castle_2.break_history.length-1].timestamp_break - post0;
        jQuery.each(cast.castle_2.break_history, function (k, breaks_h) {
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
        $("#"+ i).append(castDet);

        // CAST 3
        castDet = $("<br><div class='castle_1'><p class='castle_name'>"+ cast.castle_3.castle_name +" 3 </p></div>");
        breaks = $("<div class='break_timeline'><div class='timeline_color'></div></div>");
        post0 = cast.castle_3.break_history[0].timestamp_break;
        post100 = cast.castle_3.break_history[cast.castle_3.break_history.length-1].timestamp_break - post0;
        jQuery.each(cast.castle_3.break_history, function (k, breaks_h) {
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
        $("#"+ i).append(castDet);

        // CAST 4
        castDet = $("<br><div class='castle_1'><p class='castle_name'>"+ cast.castle_4.castle_name +" 4 </p></div>");
        breaks = $("<div class='break_timeline'><div class='timeline_color'></div></div>");
        post0 = cast.castle_4.break_history[0].timestamp_break;
        post100 = cast.castle_4.break_history[cast.castle_4.break_history.length-1].timestamp_break - post0;
        jQuery.each(cast.castle_4.break_history, function (k, breaks_h) {
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
        $("#"+ i).append(castDet);

        // CAST 5
        castDet = $("<br><div class='castle_1'><p class='castle_name'>"+ cast.castle_5.castle_name +" 5 </p></div>");
        breaks = $("<div class='break_timeline'><div class='timeline_color'></div></div>");
        post0 = cast.castle_5.break_history[0].timestamp_break;
        post100 = cast.castle_5.break_history[cast.castle_5.break_history.length-1].timestamp_break - post0;
        jQuery.each(cast.castle_5.break_history, function (k, breaks_h) {
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
        $("#"+ i).append(castDet);
        $("#"+ i).append("<br>");
    });
}