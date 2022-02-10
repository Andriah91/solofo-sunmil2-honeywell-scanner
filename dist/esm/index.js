import { registerPlugin } from '@capacitor/core';
const ScannerLaser = registerPlugin('ScannerLaser', {
    web: () => import('./web').then(m => new m.ScannerLaserWeb()),
});
export * from './definitions';
export { ScannerLaser };
//# sourceMappingURL=index.js.map