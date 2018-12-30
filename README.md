This application tried out label-based selection of Envoys using MongoDB as the backing store.

# Running locally

## Setup

Start a MongoDB container by running the following in the workspace:

```bash
docker-compose up -d
```

## Running

Run the main Spring Boot application class or start from the command-line with

```bash
mvn spring-boot:run
```

# GraphQL operations

The application exposes a GraphQL endpoint that can be `POST`ed at http://localhost:8080/graphql,
such as with curl:

```bash
curl --request POST \
  --url http://localhost:8080/graphql \
  --header 'content-type: application/json' \
  --data '{"query":"{\n  envoy {\n    id\n    labels\n  }\n}"}'
```

## "Attach" Envoys

Envoys can be simulated by attaching with the mutation:
```graphql
mutation AttachEnvoy($labels:[String!]!)
{
  attachEnvoy(labels:$labels) {
    id
  }
}
```

given the query variables:
```json
{
	"labels": [
		"zone=west",
		"access=public",
		"supports=ping",
    "supports=http"
	]
}
```

For experimentation, you can "attach" several with the same labels and some more with varying the
`zone` and `supports` options. For example, create a few with `zone=west`, `zone=east`, and one
or two with the same zone(s) but change the `supports` to just `supports=ssh`.

## Retrieve all "attached" Envoys

Use the query:
```graphql
{
  envoy {
    id
    labels
  }
}
```

## Retrieve a specific Envoy

```graphql
{
  envoy(id:"5c2938ea1155d64a1cd2aa61") {
    id
    labels
  }
}
```

## Find candidate Envoys

To simulate the selection of a particular Monitor to an available Envoy, the following query can
be used:
```graphql
query FindCandidates($labels:[String!]!)
{
  findCandidatesForMonitor(labels:$labels) {
    id
    labels
  }
}
```

with the query variables, such as:
```json
{
  "labels": ["zone=west", "supports=ping"]
}
```

## "Detach" all Envoys

```graphql
mutation
{
  detachAllEnvoys {
    count
  }
}
```

## Detach a specific Envoy

```graphql
mutation
{
  detachEnvoy(id:"5c293ed41155d662341f7c26") {
    count
  }
}
```