import { zoomElement } from './modules/motion-events.js';
import { shrinkElement } from './modules/motion-events.js';

import * as navigationBar from './modules/navigation-bar.js';
import * as formFiller from './modules/form-filling.js';

main();

function main() {
    navigationBar.addActiveStyleForButtons();
    navigationBar.addEventListenersForButtons();
    formFiller.AddEventListenersForCancelAndReturnButtons();
}
