using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class StoneCollision : MonoBehaviour
{
    private BonfireLogic bonfireLogic;
    private int collisionCount = 0;

    // Start is called before the first frame update
    void Start()
    {
        bonfireLogic = GetComponentInParent<BonfireLogic>();
    }


    private void OnCollisionEnter(Collision collision)
    {
        if(collision.gameObject.tag == "Stone")
        {
            ContactPoint contactPoint = collision.GetContact(0);
            bonfireLogic.Spark(collision.GetContact(0));
            collisionCount++;

            if (collisionCount == 3)
            {
                bonfireLogic.SpawnFire();
            }
        }
    }

}
