## SHACL shapes for W3C PROV-0-20130430

The project bundles a data management oriented SHACL version of W3C PROV-O (http://www.w3.org/ns/prov-o-20130430)
and a workbench allowing to test the SHACL shapes.
 

### Schema format

The SHACL shapes are defined and combined in an envelop that conforms to [Nexus KG schema format](https://bbp-nexus.epfl.ch/dev/schema-documentation/documentation/shacl-schemas.html#nexus-kg-schemas).


### Workbench

The workbench scala module depends on the [nexus-commons](https://github.com/bluebrain/nexus-commons) project.
In order to build the project, the  nexus-commons project has to be published locally until stable version of it is released.

To use the workbench (when located in the nexus-prov folder):

* run the 'sbt' command

```
sbt
```

* and then run 'test' command

```
test
```
All valid data defined in prov/test/resources/data/{org}/{domain}/{schemaname}/* will be checked for validation success with respect to their corresponding schemas defined in prov/src/main/resources/schemas/{org}/{domain}/{schemaname}/*.
On the opposite all invalid data defined in prov/test/resources/invalid/{org}/{domain}/{schemaname}/* will be checked for validation failure with respect to their corresponding schemas defined in prov/src/main/resources/schemas/{org}/{domain}/{schemaname}/*.