# REST API

## Table of Contents

- [`GET /api/things`](#get-apithings)
- [`GET /api/things/{thing}`](#get-apithingsthing)
- [`POST /api/things`](#post-apithings)
- [`PUT /api/things/{thing}`](#put-apithingsthing)
- [`DELETE /api/things/{thing}`](#delete-apithingsthing)
- [`GET /api/things/{thing}/shadow`](#get-apithingsthingshadow)
- [`POST /api/things/{thing}/shadow`](#post-apithingsthingshadow)
- [`GET /api/things/{thing}/shadow/violations`](#get-apithingsthingshadowviolations)

## `GET /api/things`

Returns a list of things.

```text
Content-Type: application/json
```

```json
{
  "content": [
    {
      "id": "d2bfcd45-94a7-43c9-98af-87893cfc2c66",
      "name": "SmartCamera03",
      "created_date": "2021-10-01T11:32:12.671Z",
      "last_modified_date": "2021-10-01T12:31:42.239Z",
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 23,
  "totalPages": 2
}
```

## `GET /api/things/{thing}`

Returns the details of given thing.

```text
Content-Type: application/json
```

```json
{
  "id": "d2bfcd45-94a7-43c9-98af-87893cfc2c66",
  "name": "SmartCamera03",
  "created_date": "2021-10-01T11:32:12.671Z",
  "last_modified_date": "2021-10-01T12:31:42.239Z",
  "version": 5
}
```

## `POST /api/things`

Create a new thing.

```text
Content-Type: application/json
```

```json
{
  "id": "d2bfcd45-94a7-43c9-98af-87893cfc2c66",
  "name": "SmartCamera03"
}
```

New thing is created with default `0` version and proper metadata.

```text
Content-Type: application/json
```

```json
{
  "id": "d2bfcd45-94a7-43c9-98af-87893cfc2c66",
  "name": "SmartCamera03",
  "created_date": "2021-10-01T11:32:12.671Z",
  "last_modified_date": "2021-10-01T11:32:12.671Z",
  "version": 0
}
```

## `PUT /api/things/{thing}`

Update given thing.

```text
Content-Type: application/json
```

```json
{
  "name": "SmartCamera03",
  "version": 4
}
```

Thing update bumps version to `5`.

```text
Content-Type: application/json
```

```json
{
  "id": "d2bfcd45-94a7-43c9-98af-87893cfc2c66",
  "name": "SmartCamera03",
  "created_date": "2021-10-01T11:32:12.671Z",
  "last_modified_date": "2021-10-01T12:31:42.239Z",
  "version": 5
}
```

## `DELETE /api/things/{thing}`

Deletes given thing. Doesn't return any response.

## `GET /api/things/{thing}/shadow`

Returns current setup of thing shadow along, which consists of `reported` and `desired` state.

```text
Content-Type: application/json
```

```json
{
  "desired": {
    "light_on": true,
    "resolution": "1080p",
    "upload_url": "https://10.34.231.12:8443/cams"
  },
  "reported": {
    "light_on": true,
    "resolution": "480p",
    "upload_url": "https://10.34.231.12:8443/cams"
  },
  "version": 8
}
```

## `POST /api/things/{thing}/shadow`

Updated desired state of a thing shadow.

```text
Content-Type: application/json
```

```json
{
  "desired": {
    "light_on": false,
    "resolution": "360p",
    "upload_url": "https://10.34.231.12:8443/cams"
  },
  "version": 8
}
```

The reported state is not affected by desired state change. Communication between server and thing
will eventually make states consistent.

```text
Content-Type: application/json
```

```json
{
  "desired": {
    "light_on": false,
    "resolution": "360p",
    "upload_url": "https://10.34.231.12:8443/cams"
  },
  "reported": {
    "light_on": true,
    "resolution": "1080p",
    "upload_url": "https://10.34.231.12:8443/cams"
  },
  "version": 9
}
```

## `GET /api/things/{thing}/shadow/violations`

Messages sent over MQTT (or AMQP) are being validated and quietly discarded if validation
constraints aren't met. Violations are available over REST API.

```text
Content-Type: application/json
```

```json
{
  "content": [
    {
      "action_type": "reported_update",
      "violations": [
        {
          "path": "reported.qwertyuiopoghyjrfogsdfogjdfgsldfkjgsldkfgjhsldfkghjsfdghklsfgjhslfjghslfdjgs;dfljhs;dlfkjh;sdlfkghjs;dflkgjsld;fgkjs;dflgksjdfl;ghsjdfhlkdjflghkdfgjdfghfghfghfiuytrewqwertyuioiuytrewqwertyuiopoiuytrewsdfghuytfghjuytfghjiuytfghjuygtbnjuygvbnjuygtvbnhjytgfvbhgfvbhytfvbhytrfvghuytfvbnjuyg",
          "message": "must have field name matching ^[a-zA-Z]\\w*$"
        },
        {
          "path": "reported.'qwertyuiopoghyjrfogsdfogjd...'",
          "message": "must have field name no longer than 256"
        }
      ],
      "created_date": "2021-10-10T07:46:38Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 23,
  "totalPages": 2
}
```
