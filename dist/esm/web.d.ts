import { WebPlugin } from '@capacitor/core';
import type { ScannerLaserPlugin } from './definitions';
export declare class ScannerLaserWeb extends WebPlugin implements ScannerLaserPlugin {
    notifier(): void;
    stop(): void;
    scan(): void;
}
