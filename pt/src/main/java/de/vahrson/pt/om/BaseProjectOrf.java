package de.vahrson.pt.om;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.torque.TorqueException;
import org.apache.torque.om.BaseObject;
import org.apache.torque.om.ComboKey;
import org.apache.torque.om.DateKey;
import org.apache.torque.om.NumberKey;
import org.apache.torque.om.ObjectKey;
import org.apache.torque.om.SimpleKey;
import org.apache.torque.om.StringKey;
import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;
import org.apache.torque.util.Transaction;

   
      
   
/**
 * This class was autogenerated by Torque on:
 *
 * [Thu Mar 17 11:26:19 CET 2005]
 *
 * You should not use this class directly.  It should not even be
 * extended all references should be to ProjectOrf
 */
public abstract class BaseProjectOrf extends BaseObject
{
    /** The Peer class */
    private static final ProjectOrfPeer peer =
        new ProjectOrfPeer();

                  
        /**
         * The value for the project_id field
         */
        private int project_id;
              
        /**
         * The value for the orf_id field
         */
        private int orf_id;
      
      
        /**
         * Get the ProjectId
         *
         * @return int
         */
        public int getProjectId()
        {
            return project_id;
        }

                                                      
        /**
         * Set the value of ProjectId
         *
         * @param v new value
         */
        public void setProjectId(int v) throws TorqueException
        {
          


         if (this.project_id != v)
        {
             this.project_id = v;
            setModified(true);
        }

                                          
                if (aProject != null && !(aProject.getProjectId()==v))
                {
            aProject = null;
        }
          
                       }


        /**
         * Get the OrfId
         *
         * @return int
         */
        public int getOrfId()
        {
            return orf_id;
        }

                                                      
        /**
         * Set the value of OrfId
         *
         * @param v new value
         */
        public void setOrfId(int v) throws TorqueException
        {
          


         if (this.orf_id != v)
        {
             this.orf_id = v;
            setModified(true);
        }

                                          
                if (aOrf != null && !(aOrf.getOrfId()==v))
                {
            aOrf = null;
        }
          
                       }


 
     
   
             
   
       private Project aProject;

    /**
     * Declares an association between this object and a Project object
     *
     * @param v Project
     * @throws TorqueException
     */
    public void setProject(Project v) throws TorqueException
    {
           if (v == null)
        {
                        setProjectId(0);
                    }
        else
        {
            setProjectId(v.getProjectId());
        }
           aProject = v;
    }

                 
    /**
     * Get the associated Project object
     *
     * @return the associated Project object
     * @throws TorqueException
     */
    public Project getProject() throws TorqueException
    {
        if (aProject == null && (this.project_id > 0))
        {
              aProject = ProjectPeer.retrieveByPK(SimpleKey.keyFor(this.project_id));
      
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               Project obj = ProjectPeer.retrieveByPK(this.project_id);
               obj.addProjectOrfs(this);
             */
        }
        return aProject;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setProjectKey(ObjectKey key) throws TorqueException
    {
    
                                        setProjectId(((NumberKey) key).intValue());
                    }
 
   
             
   
       private Orf aOrf;

    /**
     * Declares an association between this object and a Orf object
     *
     * @param v Orf
     * @throws TorqueException
     */
    public void setOrf(Orf v) throws TorqueException
    {
           if (v == null)
        {
                        setOrfId(0);
                    }
        else
        {
            setOrfId(v.getOrfId());
        }
           aOrf = v;
    }

                 
    /**
     * Get the associated Orf object
     *
     * @return the associated Orf object
     * @throws TorqueException
     */
    public Orf getOrf() throws TorqueException
    {
        if (aOrf == null && (this.orf_id > 0))
        {
              aOrf = OrfPeer.retrieveByPK(SimpleKey.keyFor(this.orf_id));
      
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               Orf obj = OrfPeer.retrieveByPK(this.orf_id);
               obj.addProjectOrfs(this);
             */
        }
        return aOrf;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setOrfKey(ObjectKey key) throws TorqueException
    {
    
                                        setOrfId(((NumberKey) key).intValue());
                    }
    
        
    
    private static List fieldNames = null;

    /**
     * Generate a list of field names.
     *
     * @return a list of field names
     */
    public static synchronized List getFieldNames()
    {
      if (fieldNames == null)
      {
        fieldNames = new ArrayList();
            fieldNames.add("ProjectId");
            fieldNames.add("OrfId");
            fieldNames = Collections.unmodifiableList(fieldNames);
      }
      return fieldNames;
    }

    /**
     * Retrieves a field from the object by name passed in as a String.
     *
     * @param name field name
     * @return value
     */
    public Object getByName(String name)
    {
            if (name.equals("ProjectId"))
    {
              return new Integer(getProjectId());
          }
            if (name.equals("OrfId"))
    {
              return new Integer(getOrfId());
          }
            return null;
    }
    /**
     * Retrieves a field from the object by name passed in
     * as a String.  The String must be one of the static
     * Strings defined in this Class' Peer.
     *
     * @param name peer name
     * @return value
     */
    public Object getByPeerName(String name)
    {
            if (name.equals(ProjectOrfPeer.PROJECT_ID))
        {
              return new Integer(getProjectId());
          }
            if (name.equals(ProjectOrfPeer.ORF_ID))
        {
              return new Integer(getOrfId());
          }
            return null;
    }

    /**
     * Retrieves a field from the object by Position as specified
     * in the xml schema.  Zero-based.
     *
     * @param pos position in xml schema
     * @return value
     */
    public Object getByPosition(int pos)
    {
            if (pos == 0)
    {
              return new Integer(getProjectId());
          }
            if (pos == 1)
    {
              return new Integer(getOrfId());
          }
                return null;
    }

     


    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
             save(ProjectOrfPeer.getMapBuilder()
                .getDatabaseMap().getName());
     }

    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     * Note: this code is here because the method body is
     * auto-generated conditionally and therefore needs to be
     * in this file instead of in the super class, BaseObject.
     *
     * @param dbName
     * @throws TorqueException
     */
    public void save(String dbName) throws TorqueException
    {
        Connection con = null;
         try
        {
            con = Transaction.begin(dbName);
            save(con);
            Transaction.commit(con);
        }
        catch(TorqueException e)
        {
            Transaction.safeRollback(con);
            throw e;
        }

     }

      /** flag to prevent endless save loop, if this object is referenced
        by another object which falls in this transaction. */
    private boolean alreadyInSave = false;
      /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.  This method
     * is meant to be used as part of a transaction, otherwise use
     * the save() method and the connection details will be handled
     * internally
     *
     * @param con
     * @throws TorqueException
     */
    public void save(Connection con) throws TorqueException
    {
        if (!alreadyInSave)
      {
        alreadyInSave = true;



  
        // If this object has been modified, then save it to the database.
        if (isModified())
        {
            if (isNew())
            {
                ProjectOrfPeer.doInsert((ProjectOrf) this, con);
                setNew(false);
            }
            else
            {
                ProjectOrfPeer.doUpdate((ProjectOrf) this, con);
            }
        }

              alreadyInSave = false;
      }
      }




    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
        return null;
    }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
     * It then fills all the association collections and sets the
     * related objects to isNew=true.
     */
    public ProjectOrf copy() throws TorqueException
    {
        return copyInto(new ProjectOrf());
    }

    protected ProjectOrf copyInto(ProjectOrf copyObj) throws TorqueException
    {
        copyObj.setProjectId(project_id);
        copyObj.setOrfId(orf_id);

        

  
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public ProjectOrfPeer getPeer()
    {
        return peer;
    }
}
