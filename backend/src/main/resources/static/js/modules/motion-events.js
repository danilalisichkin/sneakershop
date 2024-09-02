export function zoomElement(el, percent, time, initialWidth) {
    let start = Date.now();
    let targetWidth = initialWidth * (1 + percent / 100);

    let timer = setInterval(function() {
        let timePassed = Date.now() - start;
        if (timePassed >= time) {
            clearInterval(timer);
            el.style.width = targetWidth + 'px';
            return;
        }

        let width = initialWidth + (targetWidth - initialWidth) * (timePassed / time);
        el.style.width = width + 'px';
    }, 20);
}

export function shrinkElement(el, time, initialWidth) {
    let start = Date.now();
    let targetWidth = initialWidth;

    let timer = setInterval(function() {
        let timePassed = Date.now() - start;
        if (timePassed >= time) {
            clearInterval(timer);
            el.style.width = targetWidth + 'px';
            return;
        }

        let width = initialWidth - (initialWidth - targetWidth) * (timePassed / time);
        el.style.width = width + 'px';
    }, 20);
}
