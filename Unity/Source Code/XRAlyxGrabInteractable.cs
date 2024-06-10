using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.XR.Interaction.Toolkit;

public class XRAlyxGrabInteractable : XRGrabInteractable
{
    public float VelocityThreshold = 2;
    public float JumpAngleInDegree = 60;
    private XRRayInteractor RayInteractor;
    private Vector3 prevPos;
    private Rigidbody InteractableRigidbody;
    private bool canJump = true;

    protected override void Awake()
    {
        base.Awake();
        InteractableRigidbody = GetComponent<Rigidbody>();
    }

    private void Update()
    {
        if(isSelected && firstInteractorSelecting is XRRayInteractor && canJump == true)
        {
            Vector3 velocity = (RayInteractor.transform.position - prevPos) / Time.deltaTime;
            prevPos = RayInteractor.transform.position;
            if(velocity.magnitude > VelocityThreshold )
            {
                Drop();
                InteractableRigidbody.velocity = ComputeVelocity();
                canJump = false;
            }
            
        }
    }

    public Vector3 ComputeVelocity()
    {
        Vector3 diff = RayInteractor.transform.position  - transform.position;
        Vector3 diffXZ = new Vector3(diff.x, 0, diff.z);
        float diffXZLength = diffXZ.magnitude;
        float diffYLength = diff.y;

        float angleInRadian = JumpAngleInDegree * Mathf.Deg2Rad;
        float jumpSpeed = Mathf.Sqrt(-Physics.gravity.y * Mathf.Pow(diffXZLength, 2) / (2 * Mathf.Cos(angleInRadian) 
            * Mathf.Cos(angleInRadian) * (diffXZ.magnitude * Mathf.Tan(angleInRadian) - diffYLength)));

        Vector3 jumpVelocityVector = diffXZ.normalized * Mathf.Cos(angleInRadian) * jumpSpeed + Vector3.up * Mathf.Sin(angleInRadian) * jumpSpeed;
        return jumpVelocityVector;

    }

    protected override void OnSelectEntered(SelectEnterEventArgs args)
    {
        if(args.interactorObject is XRRayInteractor )
        {
            trackPosition = false;
            trackRotation = false;
            throwOnDetach = false;

            RayInteractor = (XRRayInteractor)(args.interactorObject);
            prevPos = RayInteractor.transform.position;
            canJump = true;
        }
        else
        {
            trackPosition = true;
            trackRotation = true;
            throwOnDetach = true;

        }
        base.OnSelectEntered(args);
    }
}
