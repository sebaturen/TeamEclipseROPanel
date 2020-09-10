$(document).ready(function() {
    /*Load a member details*/
    renderLastWeeks(new Date(), 0);
    let d = getLastWoE(new Date(), 0);
    woeBreaker(formatDateToAPI(d));
});