# Text Transformer

![build jar and docs](https://github.com/markrz-0/text-transformer/actions/workflows/build.yml/badge.svg)

For people working with text data, our Text Transformer application will allow you to transform text data (e.g. change case, eliminate duplicates, etc.). The application will be available via GUI as well as remote API, thanks to which it will be possible to integrate it with existing tools.

## List of endpoints

### POST `/transform`

Accepts JSON:
```json
{
    "text": "the text to apply transformation to",
    "transformation": [
        "array of",
        "different transformation names",
        "they will be applied in order"
        "see below"
    ]
}
```

Returns JSON
```json
{
    "text": "text with all transformations applied",
    "error": "error message or null"
}
```

## List of transformations

|name|description|example|
|-----|------|----|
|`lower`|changes all letters to lowercase|`TeXt` -> `text`|
|`upper`|changes all letters to uppercase|`TeXt` -> `TEXT`|
|`capitalize`|capitalizes every word|`teXt tExT` -> `Text Text`|
|`reverse`|reverse the order of letters while preserving the case|`MirEk` -> `KerIm`|
|`repeated-words`|removes consecutive repeated words|`my my super super super text` -> `my super text`|
