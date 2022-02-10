import Foundation

@objc public class ScannerLaser: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
