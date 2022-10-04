class TreeNode {
    var value: Int = 0
    var char: Char? = null

    var depth = 0

    var childs: MutableMap<TreeNode, Char> = mutableMapOf()
}