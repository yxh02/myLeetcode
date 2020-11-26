import java.util.*;

public class leetcode {

    public static void main(String[] args) {

        leetcode leet = new leetcode();

        String test1 = leet.addBinary("1010", "1011");
        System.out.println("test1 = "+test1);

        int mySqrtRes = leet.mySqrt(9);
        System.out.println("mySqrtRes = "+mySqrtRes);

        int climbNum = leet.climbStairs(4);
        System.out.println("climbNum = "+climbNum);

        boolean isPalindromeRes = leet.isPalindrome(10);
        System.out.println("isPalindromeRes = "+isPalindromeRes);

        int romanInt = leet.romanToInt("LVIII");
        System.out.println("romanInt = "+romanInt);

        String[] test = new String[]{"flower","flow","flight"};
        String Common = leet.longestCommonPrefix(test);
        System.out.println("Common = "+Common);


        boolean isValidRes = leet.isValid("([)]");
        System.out.println("isValidRes = "+isValidRes);

        String countAndSayres = leet.countAndSay(2);
        System.out.println("countAndSayres = "+countAndSayres);

        int lengthOfLastWord = leet.lengthOfLastWord("a ");
        System.out.println("lengthOfLastWord = "+lengthOfLastWord);

        int smallestRepunitDivByKRes = leet.smallestRepunitDivByK(1);
        System.out.println("smallestRepunitDivByKRes = "+smallestRepunitDivByKRes);

        int titleToNumberRes = leet.titleToNumber("B");
        System.out.println("titleToNumberRes = "+titleToNumberRes);

    }





    //二进制求和
    public String addBinary(String a, String b){
        //test yxhbranch
        StringBuffer res = new StringBuffer();
        int carry = 0;
        int length = Math.max(a.length(), b.length());

        for (int i = 0; i < length; i++){
            carry += i<a.length()? (a.charAt(a.length() - 1 - i)- '0') : 0;
            carry += i<b.length()? (b.charAt(b.length() - 1 - i)- '0') : 0;

            res.append((char)(carry % 2 + '0'));

            carry /= 2;
        }

        if (carry>0){
            res.append('1');
        }

        res.reverse();

        return res.toString();
    }

    //x的平方根 1.数学替换法：x根号 = x^(1/2) = (e^(lnx))^(1/2) = (e)^(1/2 * lnx)
    //二分查找:
    public int mySqrt(int x){
        if (x == 0) return 0;

        long left = 0, right = x/2 + 1, res = -1;

        while (left < right){
            long mid = (left + right + 1) / 2;
            if (mid * mid <= x){
                res = mid;
                left = mid;
            }else {
                right = mid - 1;
            }
        }
        return  (int)res;
    }

    //爬楼梯
    public int climbStairs(int n) {
        int[] blog = new int[n];
        if (n == 1 || n ==2 || n == 3) return n;

        blog[0] = 1;
        blog[1] = 2;
        blog[2] = 3;

        for (int i = 3; i < n ; i++){
            blog[i] = blog[i - 1] + blog[i - 2];
        }

        return blog[n-1];

        /**
         * 优化：不使用数组，只使用a，b，c记录
         * a = 0, b = 0, c = 1
         * for(int i = 1; i<= n; i++){
         *     a = b;
         *     b = c;
         *     c = a + b;
         * }
         * return c;
         * **/
    }

    //两数之和:使用哈希表进行查找
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int res = target - nums[i];
            if (map.containsKey(res) && map.get(res) != i) {
                return new int[]{map.get(res), i};
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    //回文数********
    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        int div = 1;

        if (x / div >= 10) div *= 10;

        while (x < 10){
            int tail = x % 10;
            int head = x / div;
            if (tail != head)
                return false;
            x = (x % div) / 10;
            div /= 100;
        }
        return true;
    }

    //罗马数字
    public int romanToInt(String s) {
        int sum = 0;

        for (int i = 0; i < s.length() ; i++){

            if ((i != s.length() -1) &&
                    (((s.charAt(i+1) == 'V' || s.charAt(i+1) == 'X') && s.charAt(i) == 'I') ||
                    ((s.charAt(i+1) == 'L' || s.charAt(i+1) == 'C') && s.charAt(i) == 'X') ||
                    ((s.charAt(i+1) == 'D' || s.charAt(i+1) == 'M') && s.charAt(i) == 'C')) ){
                sum += getNum(s.charAt(i+1)) - getNum(s.charAt(i));
                i = i + 1;
            }else {
                sum += getNum(s.charAt(i));
            }
        }
        return sum;
    }
    public int getNum(char c){
        if (c == 'I') return 1;
        if (c == 'V') return 5;
        if (c == 'X') return 10;
        if (c == 'L') return 50;
        if (c == 'C') return 100;
        if (c == 'D') return 500;
        if (c == 'M') return 1000;
        return -1;
    }

    //最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        //二分查找:只能是最段串的一部分
        if (strs == null || strs.length == 0) return "";

        int minLenth = Integer.MAX_VALUE;
        for (String str : strs){
            minLenth = Math.min(minLenth, str.length());
        }
        int low = 0 , heigh = minLenth;
        while (low < heigh){
            int mid = (low + heigh + 1)/2;
            if (isCommon(strs, mid)){
                low = mid;
            }else {
                heigh = mid - 1;
            }
        }

        return strs[0].substring(0,low);

    }
    public boolean isCommon(String[] strs, int mid){
        String Com = strs[0].substring(0, mid);

        for (int i = 1; i < strs.length; i++){
            for (int j = 0; j < mid; j++){
                if (strs[i].charAt(j) != Com.charAt(j)){
                   return false;
                }
            }
        }
        return true;
    }

    //括号匹配
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{'){
                    stack.push(s.charAt(i));
            }else {
                if (stack.empty()) return false;
                else {
                    if (s.charAt(i) == ')'){
                        if (stack.peek() != '(') return false;
                        else stack.pop();
                    }
                    if (s.charAt(i) == ']'){
                        if (stack.peek() != '[') return false;
                        else stack.pop();
                    }
                    if (s.charAt(i) == '}'){
                        if (stack.peek() != '{') return false;
                        else stack.pop();
                    }
                }
            }
        }
        if (stack.empty()) return true;
        else return false;
    }
    //合并有序链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1), p = head;

        while (l1 != null && l2 != null){
            if (l1.val <= l2.val){
                p.next = l1;
                l1 = l1.next;
                p = p.next;
            }else {
                p.next = l2;
                l2 = l2.next;
                p = p.next;
            }
        }

        while (l1 != null){
            p.next = l1;
            l1 = l1.next;
            p = p.next;
        }

        while (l2 != null){
            p.next = l2;
            l2 = l2.next;
            p = p.next;
        }

        return head.next;
    }

    //外观数列
    /*
    * 未完成
    *
    *
    **/
    public String countAndSay(int n) {

        if (n == 1) return "1";

        Queue<Integer>  queue = new LinkedList<Integer>();
        queue.offer(1);
        queue.offer(-1);
        for (int i = 2; i < n; i++){
            int head = queue.poll();
            int head_num = 1;
            System.out.println("i = "+1+"head = "+head+"head_num = "+head_num);

            while (queue.peek() != -1){
                int out = queue.poll();
                if (out == head) head_num++;
                else {
                    queue.offer(head_num);
                    queue.offer(head);
                    head = out;
                    head_num = 1;
                }
            }
            System.out.println("1 = "+i+"head = "+head+"head_num = "+head_num);
            queue.offer(head_num);
            queue.offer(head);
            queue.poll();
            queue.offer(-1);
        }
        int f_head = queue.poll();
        int f_head_num = 1;
        StringBuffer reSrt = new StringBuffer();

        while (queue.peek() != -1){
            int out = queue.poll();
            if (out == f_head) f_head_num++;
            else {
                reSrt.append(f_head_num);
                reSrt.append(f_head_num);
                f_head = out;
                f_head_num = 1;
            }
        }
        reSrt.append(f_head_num);
        reSrt.append(f_head_num);
        return  reSrt.toString();
    }

    //最后一个单词的长度 :找出两个空格间的距离
    public int lengthOfLastWord(String s) {
        int end = s.length() - 1;
        while (end >= 0 && s.charAt(end) == ' ') end--;
        if (end < 0) return 0;
        int start = end;
        while (start >= 0 && s.charAt(start) != ' ') start--;
         return end - start;
    }
    //加一:优化：若是999等，直接新建digits.length长度加一数组，0位设为1即可
    public int[] plusOne(int[] digits) {
        boolean flag = false;

        for (int i = digits.length - 1; i >= 0; i--){
            int newI = digits[i] + 1;
            if ( newI == 10) {
               digits[i] = 0;
               if (i == 0){
                   flag = true;
               }
            }
            else {
                digits[i] = newI;
                break;
            }
        }

        if (flag){
            int[] NewDid = new int[digits.length + 1];
            NewDid[0] = 1;
            return  NewDid;
        }else {
            return digits;
        }
    }

    //判断是否同一棵树
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val == q.val)
          return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        else return false;
    }

    //对称二叉树
    public boolean isSymmetric(TreeNode root) {
        return isChildSymmetric(root, root);
    }
    public boolean isChildSymmetric(TreeNode right, TreeNode left){
        if (right == null && left == null) return true;
        if (right == null || left == null) return false;
        if (right.val == left.val) return isChildSymmetric(right.right, left.left) && isChildSymmetric(right.left, left.right);
        else return false;
    }

    //二叉树最大深度
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        else {
            return 1+Math.max(maxDepth(root.left), maxDepth(root.right));
        }
    }

    //二叉树的层次遍历2    利用广度优先搜索算法
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) return new LinkedList<>();

        List<List<Integer>> ResList = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);

        while (!queue.isEmpty()){                       //保证广度优先搜索进行（使用队列）
            List<Integer> inList = new LinkedList<>();
            int size = queue.size();
            while (size > 0){                           //处理队列内部子节点，同时添加到inList
                TreeNode out = queue.poll();
                inList.add(out.val);

                if (out.left != null) queue.offer(out.left);
                if (out.right!= null) queue.offer(out.right);

                size --;
            }

            ResList.add(0, inList);
        }

        return ResList;
    }

    //可被k整除的最小整数 x(i+1)%k = (10*(x(i) % k) + 1) % k 退出循环条件
    public int smallestRepunitDivByK(int K) {

        if (K % 5 == 0 || K % 2 == 0) return -1;

        int a1 = 1;
        int i = 1;
        int last = 0;
        while (a1 % K != 0){
            a1 = a1 % K;
            a1 = 10 * a1 + 1;
//            if (a1 % K == last) return -1;
//            else last = a1 % K;
            i++;
        }
        return i;
    }

    //判断二叉平衡树
    //求树的高度
    public int heightOfTree(TreeNode root){
        if (root == null) return 0 ;
        else return Math.max(heightOfTree(root.left) , heightOfTree(root.right)) + 1;
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        return Math.abs(heightOfTree(root.left) - heightOfTree(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }
    //二叉树的最小深度
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) return 1 + minDepth(root.right);
        if (root.right == null) return 1 + minDepth(root.left);
        return 1 + Math.min(minDepth(root.left ) , minDepth(root.right));
    }

    //路径总和
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        if (root != null && root.right == null && root.left == null){
            if (root.val == sum) return true;
            else return false;
        }
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    //唯一摩尔斯密码词   set内不包含重复的元素
    public int uniqueMorseRepresentations(String[] words) {
        String[] code = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        Set<String> set = new HashSet();

        for (int i=0;i < words.length; i++){
            StringBuilder stringBuilder = new StringBuilder();
            for ( char c : words[i].toCharArray()){
                stringBuilder.append(code[c - 'a']);
            }
            set.add(stringBuilder.toString());
        }

        return set.size();
    }
    //Excel表序列号  26进制
    public int titleToNumber(String s) {
        int re = 0;
        int b = 0;
        for (int i = s.length()-1;i>=0; i--){
            int dis = (s.charAt(i) - 'A') + 1;
            re += (int )Math.pow(26, b) * dis;
            b++;
        }
        return  re;
    }

    /*阶乘后尾数中0的个数
      一个树乘上10，尾数就会多一个零 ---> 计算乘几个10即可判断尾数0个数
      10 = 2 * 5 ----> 判断2 和 5组合个数
      2的倍数比5多  ---> 计算5的倍数的个数
      每隔5个数有一个5，25个数有2个5，125个有3个5。。。。
      总5个数 = n/5 + n/25 + n/125 + ...
     */
    public int trailingZeroes(int n) {
        int re = 0;
        while (n / 5 != 0){
            re += n / 5;
            n = n / 5;
        }
        return re;
    }

    //旋转数组
    /*
     * 1.新建数组，转移后位置为 （i + k） % nums.length
     *
     */
    public void rotate(int[] nums, int k) {
        int[] Renum = new int[nums.length];

        for (int i = 0 ; i < nums.length; i++){
            int position = (i + k) % nums.length;
            Renum[position] = nums[i];
        }

        for (int i = 0; i < nums.length; i++){
            nums[i] = Renum[i];
        }
    }

    //颠倒二进制位
    /*
     *   十进制位数颠倒: re = re * 10 + n % 10; n = n / 10;
     *   二进制位数颠倒： re = re * 2 + n % 2; n = n / 2;
     *   问题：1.补零 2.负数区域
     *   -----> 使用位运算
     */

    public int reverseBits(int n) {
        int re = 0;
        for (int i = 0 ; i< 32; i++){
            re = (re << 1) + (n & 1);
            n = n >> 1;
        }

        return  re;
    }

    //branch test
    
}
