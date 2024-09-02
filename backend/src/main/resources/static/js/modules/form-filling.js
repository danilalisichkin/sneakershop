export function AddEventListenersForCancelAndReturnButtons() {
    document.querySelectorAll('.cancel-button').forEach(item => {
        item.addEventListener('click', () => {
            window.history.back();
        });
    });
}
