package com.campercontact.techradar

import software.amazon.awscdk.CfnOutput
import software.amazon.awscdk.RemovalPolicy
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.iam.AnyPrincipal
import software.amazon.awscdk.services.iam.Effect
import software.amazon.awscdk.services.iam.PolicyStatement
import software.amazon.awscdk.services.s3.BlockPublicAccess
import software.amazon.awscdk.services.s3.Bucket
import software.amazon.awscdk.services.s3.BucketProps
import software.amazon.awscdk.services.s3.deployment.BucketDeployment
import software.amazon.awscdk.services.s3.deployment.BucketDeploymentProps
import software.amazon.awscdk.services.s3.deployment.Source
import software.constructs.Construct

class TechRadarWebSiteStack @JvmOverloads constructor(
    scope: Construct,
    id: String,
    props: StackProps? = null
) : Stack(scope, id, props) {
    init {
//        todo werkt nog niet, zie bestaande bucket cc-tech-radar (handmatig aangemaakt)
        val websiteBucket = Bucket(
            this, "WebsiteBucket", BucketProps.builder()
                .websiteIndexDocument("index.html")
                .blockPublicAccess(BlockPublicAccess.BLOCK_ACLS)
                .removalPolicy(RemovalPolicy.DESTROY)
//                .enforceSsl(true)
                .build()
        )

        websiteBucket.addToResourcePolicy(
            PolicyStatement.Builder.create()
                .actions(listOf("s3:GetObject"))
                .resources(listOf("arn:aws:s3:::${websiteBucket.bucketName}/*"))
                .effect(Effect.ALLOW)
                .principals(listOf(AnyPrincipal()))
                .build()

        )

        BucketDeployment(
            this, "deploy-tech-radar", BucketDeploymentProps.builder()
                .sources(listOf(Source.asset("../campercontact")))
                .destinationBucket(websiteBucket)
                .build()
        )

        CfnOutput.Builder.create(this, "bucket-url")
            .exportName("tech-radar-bucker-url")
            .value(websiteBucket.bucketWebsiteUrl)
            .build()
    }
}
