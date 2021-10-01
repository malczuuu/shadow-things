# MQTT API

## Table of Contents

- [Topics](#topics)
- [Messages](#messages)

## Topics

| topic name                  | description                                         |
| --------------------------- | --------------------------------------------------- |
| `things/{id}/shadow/get`    | send a request to retrieve current thing shadow     |
| `things/{id}/shadow/update` | receive twin messages and responses from the server |

## Messages

- `PullDesiredState`

  ```json
  {
    "type": "pull",
    "client_token": "9ocsicS1pdaldXDqLoU9"
  }
  ```

- `PushReportedState`

  ```json
  {
    "type": "push",
    "push": {
      "reported": {
        "light_on": true,
        "resolution": "1080p",
        "upload_url": "https://10.34.231.12:8443/cams"
      }
    },
    "client_token": "9ocsicS1pdaldXDqLoU9"
  }
  ```

- `DesiredState`

  ```json
  {
    "type": "desired_state",
    "desired_state": {
      "desired": {
        "light_on": true,
        "resolution": "1080p",
        "upload_url": "https://10.34.231.12:8443/cams"
      }
    },
    "client_token": "9ocsicS1pdaldXDqLoU9"
  }
  ```

- `PushReportedStateResult`

  ```json
  {
    "type": "desired_state",
    "desired_state": {
      "desired": {
        "light_on": true,
        "resolution": "1080p",
        "upload_url": "https://10.34.231.12:8443/cams"
      },
      "reported": {
        "light_on": true,
        "resolution": "1080p",
        "upload_url": "https://10.34.231.12:8443/cams"
      }
    },
    "client_token": "9ocsicS1pdaldXDqLoU9"
  }
  ```
