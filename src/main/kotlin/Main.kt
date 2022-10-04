import java.lang.Integer.max

fun main(args: Array<String>) {

    var input = readLine()!!

    // Step 1: cout occurences of chars
    var map = HashMap<Char, Int>()
    for (c in input.toCharArray()) {
        if (c !in map.keys)
            map[c] = 1
        else
            map[c] = map[c]!! + 1
    }

    println(map)

    var nodeList = mutableListOf<TreeNode>()
    for (c in map.keys) {
        val node = TreeNode()
        node.value = map[c]!!
        node.depth = 1
        node.char = c
        nodeList.add(node)
    }

    println(
        nodeList.fold(
            mutableListOf<Any?>()
        ) { r, it ->
            r.add(it.char)
            r.add(it.value)
            r.add(it.depth)
            r
        }
    )

    // Step 2: Build tree from bottom up
    while (nodeList.size > 1) {
        nodeList = nodeList.sortedWith(compareBy<TreeNode> { it.value }.thenBy { it.depth }) as MutableList<TreeNode>
        val node1 = nodeList[0]
        val node2 = nodeList[1]
        val newNode = TreeNode()
        newNode.depth = max(node1.depth, node2.depth) + 1
        newNode.value = node1.value + node2.value
        newNode.childs[node1] = ' '
        newNode.childs[node2] = ' '
        nodeList = nodeList.filter { it != node1 && it != node2 } as MutableList<TreeNode>
        nodeList.add(newNode)
    }

    // Step 3: Print tree and set codes
    val wordMap = mutableMapOf<Char, String>()
    fun createCodebook(treeNode: TreeNode, s: String) {
        if (treeNode.childs.size == 0)
            wordMap[treeNode.char ?: treeNode.char!!] = s
        else {
            val node1 = treeNode.childs.toList()[0].first
            treeNode.childs[node1] = '0'
            createCodebook(node1, s + "0")

            val node2 = treeNode.childs.toList()[1].first
            treeNode.childs[node2] = '1'
            createCodebook(node2, s + "1")
        }
    }
    createCodebook(nodeList[0], "")

    println("Codebook:")
    println("---------------------------------")
    for (c in wordMap.keys)
        println(c + ": " + wordMap[c])
    println()
    println(input + " in Huffman-Code:")
    var code = ""
    for (c in input.toCharArray())
        code += wordMap[c] + "\n"
    println(code)
}