<div align=center>
    Make something fun, not something important.
</div>

#

### 🐌 What is SnailDb?

SnailDb is a pseudo-database that runs with the words "Make something fun, not something important" in mind. The pseudo-database 
aims not to be fast, atomic or production-ready but rather simply itself with its quirks and amazingness.

The pseudo-database is started under the thought that "I can improve my knowledge in this field by making something quirky". It is 
never intended to be performant, atomic or safe but rather it is created to become a learning experience for me.

SnailDb has several quirks with the following major points:
- It's a pseudo-database that runs on HTTP protocol and not in raw TCP or even websockets.
- SNQL (application/x.snail.snql) is designed to be as weird as possible.
- All the data is stored unencrypted or even secured, it's all there for you to see.

### 🌃 What is SNQL?

Snail Query Language or SNQL (application/x.snail.snql) is the query language of SnailDb. It's designed with the same mindset as 
the database with even more weirdness. SNQL's parser is based on simple (and possibly bad) regular expressions and simple parsers 
that aims to simply be a snail.

SNQL has a few very simple rules that can cause chaos like minified JSON and that is:
- All elements (arrays, objects, etc.) are values.
- The keyword AND (all capitalized) is reserved for delimiters e.g. in multi-attributed objects or arrays.
- Objects must be wrapped in parenthesis (**(** and **)**) (e.g. `(hello="world" AND good="night")`).
- Arrays must be wrapped in square brackets (**[** and **]**) (e.g. `(array=["hello" AND "world"])`).
- Numbers can be typed with long (int64), int (int32) by writing the type before the number (e.g. `(number=(long)123)`).
- Numbers can also be not typed with the parser determining which size is best between int64 and int32 (e.g. `(number=123)`).
- Strings must be always enclosed in double quotations (e.g. `(hello="world")`).
- Decimals cannot be like integers and must always have a penny (e.g. `(decimal=0.0)`).
- Snake-casing is more preferred over camel case.

A simple example of a SNQL query would be:
```snql
(database="snail" AND collection="users" AND content=(username="Mihou" AND token="Nogizaka46"))
```

The above query has a translated structure of:
- **database**: snail
- **collection**: users
- **content**:
  - **username**: Mihou
  - **token**: Nogizaka46

Assuming that we want to use the following route `POST /` then the following query would insert a new user 
into the collection with a username field of "Mihou" and a token of "Nogizaka46".

Although SNQL looks very simple in the surface, the complexity rises as the data grows larger and larger. You should rely 
on drivers or a generator inside to generate the SNQL for you since it will be a headache.

### 💭 What is the state of SnailDb?

SnailDb is under development with no ETA or deadline in mind.
- [x] Adds SNQL deserializer.
- [ ] Adds SNQL data files to officially declare this a pseudo-database.
- [ ] Adds operations such as INSERT, DELETE, UPDATE and GET with the use of SNQL.
- [ ] Enforces authentication via the Authorization header in the format of `user@token`.
- [ ] Adds support for indexing of data.

### 🖱️ Legality

SnailDb's creator, contributors hold no liability over any creations that uses SnailDb. Trademark usage is forbidden and 
warranty is not provided. You may use SnailDb as you wish as long as it does not affect, shame any of the contributors or its creator.