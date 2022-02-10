import { registerPlugin } from '@capacitor/core';

import type { ScannerLaserPlugin } from './definitions';

const ScannerLaser = registerPlugin<ScannerLaserPlugin>('ScannerLaser', {
  web: () => import('./web').then(m => new m.ScannerLaserWeb()),
});

export * from './definitions';
export { ScannerLaser };
