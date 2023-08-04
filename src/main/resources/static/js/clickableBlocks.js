document.addEventListener('DOMContentLoaded', function() {
    var clickableBlocks = document.querySelectorAll('.clickableBlock');

    clickableBlocks.forEach(function(block) {
        block.addEventListener('click', function() {
            var href = block.getAttribute('data-href');
            window.location.href = href;
        });
    });
});
