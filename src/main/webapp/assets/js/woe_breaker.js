$(document).ready(function() {
    /*Load a member details*/
    renderLastWeeks(moment(), 1);
    let d = getLastWoE(moment(), 1);
    const params = new URLSearchParams(window.location.search)
    if (params.has('date')) {
        woeBreaker(params.get('date'));
    } else {
        woeBreaker(d.format("YYYYMMDD"));
    }
});