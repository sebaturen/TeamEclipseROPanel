$(document).ready(function() {
    /*Load a member details*/
    renderLastWeeks(moment(), 0);
    let d = getLastWoE(moment(), 0);
    const params = new URLSearchParams(window.location.search)
    if (params.has('date')) {
        woeBreaker(params.get('date'));
    } else {
        woeBreaker(d.format("YYYYMMDD"));
    }
});