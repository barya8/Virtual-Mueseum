using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.XR.Content.Interaction;

public class SpaceOutSideControl : MonoBehaviour
{
    // Start is called before the first frame update
    public XRLever lever;
    public XRKnob knob;

    bool wasOn;

    public float forewardSpeed;
    public float sideSpeed;
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        float forewardVelocity = forewardSpeed * (lever.value ? 1 : 0);
        float sideVelocity = sideSpeed * (lever.value ? 1 : 0) * Mathf.Lerp(-1, 1, knob.value);

        Vector3 velocity = new Vector3(sideVelocity, 0, forewardVelocity);
        transform.position += velocity * Time.deltaTime;

        if(lever.value != wasOn)
        {
            if (lever.value)
            {
                AudioManager.instance.Play("Engine");
            }
            else
            {
                AudioManager.instance.Stop("Engine");

            }
        }


        wasOn = lever.value;
    }
}
