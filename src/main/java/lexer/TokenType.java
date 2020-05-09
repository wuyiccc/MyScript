package lexer;

/**
 * @author wuyiccc
 * @date 2020/5/5 10:34
 * 岂曰无衣，与子同袍~
 */
public enum TokenType {
    KEYWORD,
    OPERATOR,
    BRACKET,
    VARIABLE,
    /* 值类型 */
    STRING,
    INTEGER,
    FLOAT,
    BOOLEAN
}
