'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const ScannerLaser = core.registerPlugin('ScannerLaser', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.ScannerLaserWeb()),
});

class ScannerLaserWeb extends core.WebPlugin {
    stop() {
        throw new Error('Method not implemented.');
    }
    scan() {
        throw new Error('Method not implemented.');
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    ScannerLaserWeb: ScannerLaserWeb
});

exports.ScannerLaser = ScannerLaser;
//# sourceMappingURL=plugin.cjs.js.map
