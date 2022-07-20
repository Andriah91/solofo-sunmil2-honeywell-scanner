import { WebPlugin } from '@capacitor/core';

import type { ScannerLaserPlugin } from './definitions';

export class ScannerLaserWeb extends WebPlugin implements ScannerLaserPlugin {
    notifier(): void {
        throw new Error('Method not implemented.');
    }
    stop(): void {
        throw new Error('Method not implemented.');
    }
    scan(): void {
        throw new Error('Method not implemented.');
    }
}
