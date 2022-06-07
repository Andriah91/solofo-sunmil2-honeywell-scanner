# solofo-sunmil2-scanner

SunmiL2 laser scanner

## Install

```bash
npm install solofo-sunmil2-scanner
npx cap sync
```

## API

<docgen-index>

* [`addListener('ScannerLaserListner', ...)`](#addlistenerscannerlaserlistner)
* [`scan()`](#scan)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### addListener('ScannerLaserListner', ...)

```typescript
addListener(eventName: 'ScannerLaserListner', listenerFunc: ScanChangeListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                                              |
| ------------------ | ----------------------------------------------------------------- |
| **`eventName`**    | <code>'ScannerLaserListner'</code>                                |
| **`listenerFunc`** | <code><a href="#scanchangelistener">ScanChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### scan()

```typescript
scan() => void
```

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### ScanChangeListener

<code>(resultScan: any): void</code>

</docgen-api>
