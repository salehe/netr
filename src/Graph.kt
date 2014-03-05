package calculations

import java.util.ArrayList
import java.util.TreeSet

class Edge(val v1: Int, val v2: Int, val w: Double)

class APSPInfo(val spd: Array<Array<Double>>, val path: Array<Array<Int>>)

class Graph(val nv: Int)
{
    var edges: ArrayList<Edge> = ArrayList()

    fun addEdge(v1: Int, v2: Int, w: Double): Graph
    {
        edges.add(Edge(v1, v2, w))
        return this
    }

    fun adjMatrix(): Array<Array<Edge?>>
    {
        val am : Array<Array<Edge?>> = Array(nv, {i -> Array<Edge?>(nv, {j -> null})})

        for (e in edges)
        {
            am[e.v1][e.v2] = e
            am[e.v2][e.v1] = e
        }

        return am
    }

    fun apsp(): APSPInfo
    {
        val spd : Array<Array<Double>> = Array(nv, {i -> Array<Double>(nv, {j -> java.lang.Double.MAX_VALUE})})
        val path : Array<Array<Int>> = Array(nv, {i -> Array<Int>(nv, {j -> i})})

        for (i in 0..nv-1)
        {
            spd[i][i] = 0.0
            path[i][i] = i
        }

        for (e in edges)
        {
            spd[e.v1][e.v2] = e.w
            spd[e.v2][e.v1] = e.w
            path[e.v1][e.v2] = e.v1
            path[e.v2][e.v1] = e.v2
        }

        for (k in 0..nv-1)
            for (i in 0..nv-1)
                for (j in 0..nv-1)
                {
                    if (spd[i][j] > spd[i][k] + spd[k][j])
                    {
                        spd[i][j] = spd[i][k] + spd[k][j]
                        path[i][j] = path[k][j]
                    }
                }

        return APSPInfo(spd, path);
    }

    private fun minDistanceFromSet(target: Set<Int>, source: List<Int>, spd: Array<Array<Double>>): Pair<Int, Int>?
    {
        var minDistance = java.lang.Double.MAX_VALUE
        var minSource : Int? = null
        var minTarget : Int? = null
        for (s in source)
        {
            if (!target.contains(source))
            {
                for (t in target)
                {
                    if (spd[s][t] < minDistance)
                    {
                        minDistance = spd[s][t]
                        minSource = s
                        minTarget = t
                    }
                }
            }
        }

        if (minSource == null || minTarget == null)
            return null
        return Pair(minSource!!, minTarget!!)
    }

    fun kmb(terminals: List<Int>): Set<Edge>
    {
        val apsp = apsp()
        val spd = apsp.spd
        val path = apsp.path
        val am = adjMatrix()

        // phase 1, add

        val tVertices = TreeSet<Int>()
        val tEdges = TreeSet<Edge>()

        tVertices.add(terminals[0])

        while (true)
        {
            val next = minDistanceFromSet(tVertices, terminals, spd)
            if (next == null)
                break;
            val minSource = next.first
            val minTarget = next.second

            tVertices.add(minTarget)
            var end = minTarget
            while (true)
            {
                val nextEdge = am[path[minSource][end]][end]
                if (nextEdge == null)
                    break;
                tEdges.add(nextEdge)
                end = path[minSource][end]
            }
        }

        // phase 2, remove

        return tEdges
    }
}

fun main(args: Array<String>)
{
    var g = Graph(5)
}