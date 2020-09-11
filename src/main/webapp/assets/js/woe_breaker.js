$(document).ready(function() {
    /*Load a member details*/
    renderLastWeeks(moment(), 1);
    let d = getLastWoE(moment(), 1);
    woeBreaker(d.format("YYYYMMDD"));

});