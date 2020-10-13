$(document).ready(function () {

    // Edit character
    $('#edit_char').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let charId = button.data('char_id');
        let bg = button.data('bg');
        let action = (button.data('action') > 0)? button.data('action'):0;
        let direction = (button.data('direction') > 0)? button.data('direction'):0;

        console.log(charId, bg, action, direction);

        let modal = $(this);
        modal.find("#char_bg").val(bg);
        modal.find("#char_action").val(action);
        modal.find("#char_direction").val(direction);
        modal.find('#char_id').val(charId);
    });

});