$(document).ready(function() {
    /*Load a member details*/
    renderLastWeeks(moment(), 0);
    let d = getLastWoE(moment(), 0);
    woeBreaker(d.format("YYYYMMDD"));
});