import { WebPlugin } from '@capacitor/core';

import type { ScannerLaserPlugin } from './definitions';

export class ScannerLaserWeb extends WebPlugin implements ScannerLaserPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
