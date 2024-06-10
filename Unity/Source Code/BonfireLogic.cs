using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.XR.Interaction.Toolkit;

public class BonfireLogic : MonoBehaviour
{
 
    public GameObject FireSpawnPoint;
    public GameObject SparkParticle;
    public GameObject FireParticle;
    public Light pLight;
    public SpriteRenderer spriteRenderer;

    [HideInInspector]
    public bool isHolding = false;


    public void Spark(ContactPoint contact)
    {
        Vector3 pos = contact.point;
        GameObject spark = Instantiate(SparkParticle, pos, Quaternion.identity);
        spark.GetComponent<ParticleSystem>().Emit(1);
        
    }

    public void SpawnFire()
    {
        GameObject Fire = Instantiate(FireParticle, FireSpawnPoint.transform.position, Quaternion.identity);
        Fire.GetComponent<ParticleSystem>().Play();
        pLight.range = 40;
        Color tmp = spriteRenderer.color;
        tmp.a = 255;
        spriteRenderer.color = tmp;

    }
}
