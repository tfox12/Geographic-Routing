package georouting.vanet.voronoi;


public class VoronoiBSTree
{
    private VoronoiSweepLine  _sweepLine;
    private VoronoiBSTreeNode _root;

    
    public VoronoiBSTree(VoronoiSweepLine sweepLine)
    { 
        _root = null; 
        _sweepLine = sweepLine;
    }

    public VoronoiSite[] addSite(VoronoiSite site)
    {
        VoronoiSite[] originalBreak = { null, null };

        if(_root == null)
        {
            _root = this.new VoronoiBSTreeSiteNode(site);
        }
        else
        {
            // find the parabola that we are splitting
            VoronoiBSTreeSiteNode split = _root.findNewBreak(site);

            // add a new edge that half-plane's the 2 sites
            _sweepLine.addEdge(new VoronoiEdge(site,split);

            // create 3 new leaf nodes
            VoronoiBSTreeSiteNode left = this.new VoronoiBSTreeSiteNode(split.site());
            VoronoiBSTreeSiteNode mid = this.new VoronoiBSTreeSiteNode(site);
            VoronoiBSTreeSiteNode right = this.new VoronoiBSTreeSiteNode(split.site());
            
            // right breakpoint
            VoronoiBSTreeBreakPointNode rightBreak =
                this.new VoronoiBSTreeBreakPointNode(mid,right);
            mid.resetParent(rightBreak);
            right.resetParent(rightBreak);

            // left breakpoint
            VoronoiBSTreeBreakPointNode leftBreak =
                this.new VoronoiBSTreeBreakPointNode(left,rightBreakpoint,mid);
            left.resetParent(leftBreak);
            rightBreak.resetParent(leftBreak);

            leftBreak.resetParent(split.parent());
            split.swap(leftBreak);

            if(leftBreak.parent() != null)
            {
                VoronoiBSTreeBreakPointNode joinPoint = 
                    (VoronoiBSTreeBreakPointNode) leftBreak.parent();
                originalBreak[0] = joinPoint.leftSite();
                originalBreak[1] = joinPoint.rightSite();
            }     
        }
        return originalBreak;
    }

    public VoronoiVertexEvent[] removeNode(VoronoiSite left, VoronoiSite mid, VoronoiSite right)
    {        
        // lets do a search for left and mid in the 
        VoronoiBSTreeBreakPointNode parent = _root.findRemoveBreakpoint(left,mid,right);
        if(parent.leftSite() == left)
             parent.replaceBreak(mid,right);
        else parent.replaceBreak(mid,left);

        VoronoiBSTreeBreakPointNode child = null; 
        if(parent.getBreakPointX() < mid.x())
             child = parent.left().findRemoveBreakpoint(left,mid,right);
        else child = parent.right().findRemoveBreakpoint(left,mid,right);
        
        if(child.rightSite() == mid)
             child.parent().swap(child.left());
        else child.parent().swap(child.right());

        VoronoiVertexEvent[] result = {null,null};
        VoronoiSite nl = parent.left().nextRight();
        VoronoiSite nr = parent.right().nextLeft();
        if(nl != right)
            result[0] = new VoronoiVertexEvent(nl,left,right);
        if(nr != left)
            result[1] = new VoronoiVertexEvent(left,right,nr);

        return result;
    }

    public ArrayList<VoronoiVertexEvent> eventsForSite(VoronoiSite site)
    {
        ArrayList<VoronoiVertexEvent result = new ArrayList<VoronoiVertexEvent>();
        VoronoiSite[] prev = {null,null};
        _root.getVertexResults(prev,site,result);
        return result;
    }

//////////////////////////////////////////////////////////////////////////////////////////

    /**
        <p>represents a generic member of the bianary search tree.</p>
    */
    protected abstract class VoronoiBSTreeNode
    {
        private VoronoiBSTreeNode   _parent,
                                    _left,
                                    _right;

        public VoronoiBSTreeNode parent()   { return _parent; }
        public VoronoiBSTreeNode left()     { return _left;   }
        public VoronoiBSTreeNode right()    { return _right;  }

        public abstract VoronoiSite nextLeft();
        public abstract VoronoiSite nextRight();

        public void resetParent(VoronoiBSTreeNode newParent)
        { 
            _parent = newParent; 
        }
 
        public VoronoiBSTreeNode(VoronoiBSTreeSiteNode parent)
        {   
            _parent = parent; 
            _left   = null; 
            _right  = null; 
        }

        public VoronoiBSTreeNode(VoronoiBSTreeSiteNode parent,
                                 VoronoiBSTreeSiteNode left,
                                 VoronoiBSTreeSiteNode right)
        {   
            _parent = parent; 
            _left   = left; 
            _right  = right; 
        }

        
        public void swap(VoronoiBSTreeNode node)
        {
            parent.swap(this,node);
        }

        protected void swap(VoronoiBSTreeNode child, VoronoiBSTreeNode newChild)
        {
            if(_right == child)
            {
                _right = newChild;
            }
            else
            {
                _left = newChild;
            }
        }

        public abstract VoronoiBSTreeBreakPointNode findRemoveBreakpoint(VoronoiSite left,VoronoiSite mid,VoronoiSite right);
        public abstract VoronoiBSTreeSiteNode findNewBreak(VoronoiSite site);
        public abstract VoronoiSite[] getVertexResults(VoronoiSite[] previous,
                                              VoronoiSite addedSite,
                                              ArrayList<VoronoiVertexEvent> foundEvents);
    }

//////////////////////////////////////////////////////////////////////////////////////////

    /**
        <p> A VoronoiBSTreeSiteNode is always going to be a leaf on the search tree.
            For this reason, the left and right members are always null.</p>
    */
    protected class VoronoiBSTreeSiteNode extends VoronoiBSTreeNode
    {
        private VoronoiSite _site;
        
        public VoronoiBSTreeSiteNode(VoronoiSite site)
        {
            super(null);
            _site = site;
        }
        
        public VoronoiBSTreeSiteNode(VoronoiSite site, VoronoiBSTreeBreakPointNode parent)
        {
            super(parent);
            _site = site;
        }
        
        public VoronoiBSTreeSiteNode findNewBreak(VoronoiSite site)
        {
            return this;
        }

        public VoronoiSite[] getVertexResults(VoronoiSite[] previous,
                                              VoronoiSite addedSite, 
                                              ArrayList<VoronoiVertexEvent> foundEvents)
        {
            // make sure all previous elements are there, then make sure we don't have a double
            if((previous[0] != null) &&
               ((previous[0] == addedSite && _site != addedSite) ||
               (_site == addedSite && previous[0] != addedSite))) // only 1 site object for each site, so we can compare memloc
                    foundEvents.add(new VoronoiVertexEvent(previous[0],previous[1],_site);
                
            previous[0] = previous[1];
            previous[1] = _site;
            return previous;
        }

        public VoronoiBSTreeBreakPointNode findRemoveBreakpoint(VoronoiSite left,VoronoiSite mid,VoronoiSite right)
        {
            throw new Exception("Should have not gotten to this point");
        }
        public VoronoiSite nextLeft()
        {
            return _site;
        }
        public VoronoiSite nextRight()
        {
            return _site;
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////

    /**
        
    */
    protected class VoronoiBSTreeBreakPointNode extends VoronoiBSTreeNode
    {
        private VoronoiSite _leftSite,
                            _rightSite;

        public VoronoiSite leftSite() { return _leftSite; }
        public VoronoiSite rightSite() { return _rightSite; }

        public VoronoiBSTreeBreakPointNode(
                                           VoronoiBSTreeSiteNode left,
                                           VoronoiBSTreeSiteNode right)
        {
            super(null, left, right);
            _leftSite = left.site();
            _rightSite = right.site();
        }

        public VoronoiBSTreeBreakPointNode(
                                           VoronoiBSTreeSiteNode left,
                                           VoronoiBSTreeBreakPointNode right,
                                           VoronoiSite rightSite)
        {
            super(null, left, right);
            _leftSite = left.site();
            _rightSite = rightSite;
        }

        public VoronoiBSTreeSiteNode findNewBreak(VoronoiSite site)
        {
            if(site.x() < getBreakPointX())
                return _left.findNewBreak();
            else return _right.findNewBreak();
        }

        private double getBreakPointX()
        {
            // case 1: horizontal line
            if((float)(_leftSite.y() - _rightSite.y()) == 0)
            {
                return (_leftSite.x() + _rightSite.x()) / 2;
            }

            // case 2: vertical.... is impossible
            
            // case 3: general
    
            double m = (_leftSite.y() - _rightSite.y()) /  /* slope of the secent line */ 
                       (_leftSite.x() - _rightSite.x()) ;
            // intersection with sweep and secent line
            double x = (_sweepLine.height() - _leftSite.y()) / m + _leftSite.x();
            double t = Math.sqrt( ( Math.pow(_leftSite.x() - x                   , 2) + 
                                    Math.pow(_leftSite.y() - _sweepLine.height() , 2) ) *
                                  ( Math.pow(_rightSite.x() - x                  , 2) + 
                                    Math.pow(_rightSite.y() - _sweepLine.height(), 2) ) );
            return (_right instanceof VoronoiBSTreeBreakPointNode) ? x - t : x + t;
        }

        public VoronoiSite[] getVertexResults(VoronoiSite[] previous,
                                              VoronoiSite addedSite, 
                                              ArrayList<VoronoiVertexEvent> foundEvents)
        {
            previous = _left.getVertexResults(previous,addedSite,foundEvents);
            return     _right.getVertexResults(previous,addedSite,foundEvents);
        }
        
        public VoronoiBSTreeBreakPointNode findRemoveBreakpoint(VoronoiSite left,VoronoiSite mid,VoronoiSite right)
        {
            if((left == leftSite && mid == rightSite) ||
               (mid == leftSite && right = rightSite))
                return this;
            if(mid.x() < getBreakPointX())
                return _left.findRemoveBreakpoint(left,mid,right);
            else
                return _right.findRemoveBreakpoint(left,mid,right);
        }

        public void replaceBreak(VoronoiSite oldSite, VoronoiSite newSite)
        {
            if(leftSite == oldSite)
                leftSite = newSite;
            else if(rightSite == oldSite)
                rightSite = newSite;
        }

        public VoronoiSite nextLeft()
        {
            return left.nextLeft();
        }

        public VoronoiSite nextRight()
        {
            return right.nextRight();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////
}
