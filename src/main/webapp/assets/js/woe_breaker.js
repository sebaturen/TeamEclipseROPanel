$(document).ready(function() {
    /*Load a member details*/
    renderLastWeeks(new Date(), -1);
    let d = getLastWoE(new Date(), -1);
    woeBreaker(formatDateToAPI(d));

});