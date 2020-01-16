# ProtoBuf
- [github](https://github.com/protocolbuffers/protobuf)
- [proto3 官方文档](https://developers.google.cn/protocol-buffers/docs/proto3)

## java ~ proto
|.proto  	| C++  	 | Java         |
| --- | --- | --- |
|double		| double | double       |
|float		| float	 | float        |
|int32		| int32	 | int          |
|int64		| int64	 | long         |
|uint32	    | uint32 | int[1]       |
|uint64	    | uint64 | long[1]      |
|sint32     | int32	 | int          |
|sint64	    | int64	 | long         |
|fixed32   	| uint32 | int[1]       |
|fixed64 	| uint64 | long[1]      |
|sfixed32 	| int32	 | int          |
|sfixed64 	| int64	 | long         |
|bool       | bool	 | boolean      |
|string  	| string | String       |
|bytes	 	| string | ByteString   |

> [1] In Java, unsigned 32-bit and 64-bit integers are represented using their signed counterparts, with the top bit simply being stored in the sign bit.


## 字段修饰符

- proto2 (字段修饰符不能为空)
    - required: 必填
    - optional: 可选
    - repeated: 可重复，也即数组

- proto3 (字段修饰符可为空，舍弃了required和optional)
    - repeated: 可重复，也即数组
