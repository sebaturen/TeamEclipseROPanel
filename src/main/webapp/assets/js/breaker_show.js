const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
$(document).ready(function() {
    $(document).on('click','.woe_event_day',function(){
        woeBreaker($(this).data("time_break"));
    });

    $(document).on('click', '.hide_city', function () {
        showHideCity($(this).parent('p'), false);
    })

    $(document).on('click', '.show_city', function () {
        showHideCity($(this).parent('p'), true);
    })

});

function showHideCity(city, status) {
    let cityBlock = $(city).parents(".city_castle");
    if (status) {
        $(cityBlock).children(".castles").show('fast');
        $(city).html('<i class="fas fa-plus hide_city"></i>');
    } else {
        $(cityBlock).children(".castles").hide('fast');
        $(city).html('<i class="fas fa-plus show_city"></i>');
    }
}

function castBreaker(breakInfo) {
    jQuery.each(breakInfo, function(i, cast) {
        $("#"+ i).append("<div class='city_header'>"+ cast.map_name +"<p class='city_display_control'><i class='fas fa-minus hide_city'></i></p></div>");
        // Foreach castles
        let castles = $("<div class='castles'></div>");
        // CAST 1
        castles.append(renderCastle(cast.castle_1, 1));

        // CAST 2
        castles.append(renderCastle(cast.castle_2, 2));

        // CAST 3
        castles.append(renderCastle(cast.castle_3, 3));

        // CAST 4
        castles.append(renderCastle(cast.castle_4, 4));

        // CAST 5
        castles.append(renderCastle(cast.castle_5, 5));

        $("#"+ i).append(castles);
    });
}

function renderCastle(inf, castNumber) {
    let breakCount = inf.break_history.length-2;
    if (breakCount < 0) breakCount = "~";
    let castDet = $("<div class='castle_num'><div class='castle_name'>["+ castNumber +"] "+ inf.castle_name +"<p class='break_count'>Breaks: "+ breakCount +"</p></div>");
    let breakTimeline = $("<div class='break_timeline'></div>");
    let breaks = $("<div class='guild_breakers'><div class='guild_breakers_times'></div></div>");
    let post0 = inf.break_history[0].timestamp_break;
    let post100 = inf.break_history[inf.break_history.length-1].timestamp_break - post0;
    jQuery.each(inf.break_history, function (k, breaks_h) {
        let postEmb = ( (breaks_h.timestamp_break-post0) * 100)/post100;
        let tempClass = ""
        if (postEmb > 0) tempClass = "despIcons";
        let minBreak = new Date(breaks_h.timestamp_break);
        let diffTime = (minBreak - post0)/1000;
        let minuteDiff = Math.floor(diffTime / 60);
        let showTime;
        if (minuteDiff >= 59) {
            if (minuteDiff >= 119) {
                let tTime = minuteDiff - 120;
                if (tTime < 0) tTime = 0;
                showTime = "2:0"+ tTime;
            } else {
                let tTime = minuteDiff - 60;
                if (tTime < 0) tTime = 0;
                showTime = "1:"+ ((tTime >= 9)? tTime:"0"+ tTime);
            }
        } else {
            showTime = "0:"+ ((minuteDiff >= 9)? minuteDiff:"0"+ minuteDiff);
        }
        breaks.append(`
                <div class='breaker_point `+ tempClass +`' style="margin-left: `+ postEmb +`%">
                    <div class='breaker_guild_tl'>
                        <a href="guild_info.jsp?id=`+ breaks_h.guild_id +`" data-guild_name='`+ breaks_h.guild_name +`' data-break_time='`+ showTime +`'>
                            <img src='./assets/img/ro/guilds/emblems/Poring_`+ breaks_h.guild_id +`_`+ breaks_h.guild_emblem +`.png'>
                        </a>
                    </div>
                </div>
            `);
    })
    castDet.append(breaks);
    breakTimeline.append(castDet);
    return breakTimeline;
}

function woeBreaker(dateBreaker) {
    $(".woe_event_day").removeClass("day_selected");
    $(`.day_${dateBreaker}`).addClass("day_selected");
    $("#loading").show();
    $("#break_info").html('' +
        '<div id="prt_gld" class="city_castle"></div>' +
        '<div id="gef_fild13" class="city_castle"></div>' +
        '<div id="pay_gld" class="city_castle"></div>' +
        '<div id="alde_gld" class="city_castle"></div>' +
        '<div id="sch_gld" class="city_castle"></div>' +
        '<div id="aru_gld" class="city_castle"></div>'
    );
    $.get("rest/woe/breaker/"+ dateBreaker, function(data) {
        $("#loading").hide();
        castBreaker(data);
    }).always(function() {
        //complete();
    });
}

function renderLastWeeks(d, diff) {
    let dContent = $("#date_times");
    dContent.append("<i class='col fas fa-angle-right day_move_up'></i>");
    let i = 0;
    do {
        d = getLastWoE(d, diff)
        dContent.append("<div class='col woe_event_day day_"+ d.format("YYYYMMDD") +"' data-time_break='"+ d.format("YYYYMMDD") +"'>"+ d.format("MMM D YYYY") +"</div>");
        i++;
        d.subtract(1, 'day')
    } while (i < 5);
    dContent.append("<i class='col fas fa-angle-left day_move_down'></i>");
    dContent.children().each(function(i,li){dContent.prepend(li)})
}

function getLastWoE(d, diff) {
    return d.subtract(parseInt(d.format("d"))+diff, 'day')
}