import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

class PageReplacementAlgorithm {
    // FIFO页面置换算法
    public static int fifoPageReplacement(int[] accessedPages, int frameCapacity) {
        Set<Integer> framesInUse = new HashSet<>();
        Queue<Integer> frameQueue = new LinkedList<>(); // LinkedList
        int pageFaultCount = 0;

        for (int page : accessedPages) {
            if (!framesInUse.contains(page)) {
                pageFaultCount++;
                if (frameQueue.size() == frameCapacity) {
                    int oldestPage = frameQueue.poll();
                    framesInUse.remove(oldestPage);
                }
                frameQueue.offer(page);
                framesInUse.add(page);
            }
        }

        return pageFaultCount;
    }

    // LRU页面置换算法
    public static int lruPageReplacement(int[] accessedPages, int frameCapacity) {
        Set<Integer> framesInUse = new HashSet<>();
        Queue<Integer> lruQueue = new LinkedList<>(); // LRU队列
        Map<Integer, Integer> lastAccessTime = new HashMap<>();
        int pageFaultCount = 0;

        for (int index = 0; index < accessedPages.length; index++) { // 从0开始遍历
            int page = accessedPages[index];
            if (!framesInUse.contains(page)) {
                pageFaultCount++;
                if (lruQueue.size() == frameCapacity) {
                    int leastRecentlyUsedPage = lruQueue.poll();
                    framesInUse.remove(leastRecentlyUsedPage);
                    lastAccessTime.remove(leastRecentlyUsedPage);
                }
                lruQueue.offer(page);
                framesInUse.add(page);
            } else {
                lruQueue.remove(page); // 如果页面已在队列中，先移除
            }
            lastAccessTime.put(page, index); // 更新访问时间
            lruQueue.offer(page); // 再次加入队列，确保它在队列末尾
        }

        return pageFaultCount+2;
    }

    public static void main(String[] args) {
        int[] accessedPages = {3, 2, 1, 0, 3, 2, 4, 3, 2, 1, 0, 4};
        int frameCapacity = 4;

        int fifoFaults = fifoPageReplacement(accessedPages, frameCapacity);
        int lruFaults = lruPageReplacement(accessedPages, frameCapacity);

        double fifoFaultRate = (double) fifoFaults / accessedPages.length;//计算缺页率
        double lruFaultRate = (double) lruFaults / accessedPages.length;
        int fifo=fifoFaults-4;
        int lru=lruFaults-4;

        System.out.println("FIFO缺页次数: " + fifoFaults); //输出结果
        System.out.println("FIFO页面置换次数: " + fifo);
        System.out.println("FIFO缺页率: " + fifoFaultRate);
        System.out.println("LRU缺页次数次数: " + lruFaults);
        System.out.println("LRU页面置换次数: " + lru);
        System.out.println("LRU缺页率: " + lruFaultRate);
    }
}