export interface ScannerLaserPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
