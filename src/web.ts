import { WebPlugin } from '@capacitor/core';

import type { ScannerLaserPlugin } from './definitions';

export class ScannerLaserWeb extends WebPlugin implements ScannerLaserPlugin {
    scan(): void {
        throw new Error('Method not implemented.');
    }
}
