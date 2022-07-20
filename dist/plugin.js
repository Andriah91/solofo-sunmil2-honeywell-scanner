var capacitorScannerLaser = (function (exports, core) {
    'use strict';

    const ScannerLaser = core.registerPlugin('ScannerLaser', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.ScannerLaserWeb()),
    });

    class ScannerLaserWeb extends core.WebPlugin {
        notifier() {
            throw new Error('Method not implemented.');
        }
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

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
