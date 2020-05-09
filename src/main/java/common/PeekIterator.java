package common;

import sun.util.resources.cldr.rn.CurrencyNames_rn;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * @author wuyiccc
 * @date 2020/5/8 21:26
 * 岂曰无衣，与子同袍~
 */
public class PeekIterator<T> implements Iterator<T> {



    private Iterator<T> it;

    private LinkedList<T> queueCache = new LinkedList<>(); // 缓存队列，接受it原生迭代出的数据
    private LinkedList<T> stackPutBacks = new LinkedList<>(); // 放回队列，当next操作时，接受原生迭代出的值

    private static final int CACHE_SIZE = 10;

    private T _endToken = null;

    public PeekIterator(Stream<T> stream) {
        this.it = stream.iterator();
    }



    public PeekIterator(Stream<T> stream, T endToken) {
        this.it = stream.iterator();
        this._endToken = endToken;
    }

    public T peek() {

        if (stackPutBacks.size() > 0) {
            return this.stackPutBacks.getFirst();
        }

        if (!it.hasNext()) {
            return _endToken;
        }

        T val = this.next();
        this.putBack();
        return val;
    }


    // 放回元素
    public void putBack() {

        if (queueCache.size() > 0) {

            this.stackPutBacks.push(this.queueCache.pollLast()); // 前插
        }
    }

    @Override
    public boolean hasNext() {

        return _endToken != null || this.stackPutBacks.size() > 0 || it.hasNext();
    }


    @Override
    public T next() {


        T val = null;
        if (this.stackPutBacks.size() > 0) {
            val = this.stackPutBacks.pop();
        } else {
            if (!this.it.hasNext()) {
                T tmp = _endToken;
                _endToken = null;
                return tmp;
            }
            val = it.next();
        }


        while (queueCache.size() > CACHE_SIZE - 1) {
            queueCache.poll();
        }

        queueCache.add(val);
        return val;
    }

}
