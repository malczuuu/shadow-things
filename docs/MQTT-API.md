# MQTT API

## Table of Contents

- [Topics](#topics)
- [Messages](#messages)

## Topics

| topic name                       | description                                         |
| -------------------------------- | --------------------------------------------------- |
| `api/things/{id}/shadow/query`   | send a request to retrieve current thing shadow     |
| `api/things/{id}/shadow/update`  | send a request to update shadow reported state      |
| `api/things/{id}/shadow/receive` | receive twin messages and responses from the server |

## Messages

### `TokenEnvelope`

```json
{
  "token": "9ocsicS1pdaldXDqLoU9"
}
```

### `ShadowEnvelope`

```json
{
  "type": "shadow",
  "shadow": {
    "desired": {
      "light_on": true,
      "resolution": "1080p",
      "upload_url": "https://10.34.231.12:8443/cams"
    },
    "reported": {
      "light_on": true,
      "resolution": "1080p",
      "upload_url": "https://10.34.231.12:8443/cams"
    },
    "last_modified_date": "2021-10-01T00:23:11Z",
    "version": 31
  },
  "token": "9ocsicS1pdaldXDqLoU9"
}
```

### `ReportedEnvelope`

```json
{
  "type": "reported",
  "reported": {
    "light_on": true,
    "resolution": "1080p",
    "upload_url": "https://10.34.231.12:8443/cams"
  },
  "token": "9ocsicS1pdaldXDqLoU9"
}
```
