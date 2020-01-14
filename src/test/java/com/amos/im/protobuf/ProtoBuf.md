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


## 每个字段都必须使用以下修饰符之一进行注释

- required: 必须提供该字段的值，否则该消息将被视为“未初始化”
  > 尝试构建未初始化的消息将引发RuntimeException。解析未初始化的消息将引发IOException。
  >
  > 除此之外，必填字段的行为与可选字段完全相同。

- optional: 可能会或可能不会设置该字段。
  > 如果未设置可选字段值，则使用默认值。
  >
  > 对于简单类型，您可以指定自己的默认值，就像type在示例中为电话号码所做的那样。否则，将使用系统默认值：数字类型为零，字符串为空字符串，布尔值为false。对于嵌入式消息，默认值始终是消息的“默认实例”或“原型”，没有设置任何字段。调用访问器以获取未显式设置的可选（或必填）字段的值始终会返回该字段的默认值。

- repeated: 该字段可以重复任意次(包括零次)
  > 重复值的顺序将保留在协议缓冲区中。将重复字段视为动态大小的数组。