package com.mauricioliveira.core.data.utils

internal val TEST_FEED_JSON = """
    {
      "feed": {
        "entry": [
          {
            "im:name": {"label": "Album One"},
            "im:image": [
              {"label": "https://img.example.com/55.png", "attributes": {"height": "55"}},
              {"label": "https://img.example.com/170.png", "attributes": {"height": "170"}}
            ],
            "im:itemCount": {"label": "12"},
            "im:price": {"label": "${'$'}9.99"},
            "im:releaseDate": {"label": "2024-11-22T00:00:00-07:00", "attributes": {"label": "November 22, 2024"}},
            "id": {"attributes": {"im:id": "111"}},
            "im:artist": {"label": "Artist One"},
            "category": {"attributes": {"label": "Pop"}},
            "rights": {"label": "Copyright One"},
            "link": {"attributes": {"href": "https://music.apple.com/album/111"}}
          },
          {
            "im:name": {"label": "Album Two"},
            "im:image": [
              {"label": "https://img.example.com/55b.png", "attributes": {"height": "55"}}
            ],
            "id": {"attributes": {"im:id": "222"}},
            "im:artist": {"label": "Artist Two"},
            "category": {"attributes": {"label": "Rock"}}
          }
        ]
      }
    }
""".trimIndent()