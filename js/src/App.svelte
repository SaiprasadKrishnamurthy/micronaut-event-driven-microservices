<script lang="ts">
  import {
    Collapse,
    Navbar,
    NavbarToggler,
    NavbarBrand,
    Nav,
    NavItem,
    NavLink,
    Alert,
    Badge
  } from 'sveltestrap';

 import { Table, Form, FormGroup, FormText, Input, Label, Button, Spinner, Card, CardBody, CardHeader,CardFooter, CardText, CardSubtitle, CardTitle  } from 'sveltestrap';

 let rawValues = `A,B,C,D
A,B,X,D
A,B,P,J
A,B,P,J
A,B,P,J,M,Y
A,B,P,J,M,Y
`;
 let delimiter = ',';
 let result = null;
 let entries = [];
 let inProgress = false;

  async function doPost () {
        inProgress = true;
        entries = rawValues.split("\n");
		const res = await fetch('http://localhost:8080/api/v1/group/', {
			method: 'POST',
			headers: {
			    "Content-type": "application/json"
			},
			body: JSON.stringify({
			    entries: entries,
			    delimiter: delimiter
			})  });
		const json = await res.json();
		result = json;
		inProgress = false;
	}

</script>

<Navbar color="primary" light>
  <NavbarBrand href="/" class="mr-auto"><b>TOOLKIT</b></NavbarBrand>
</Navbar>

<br />
<Card class="mb-3">
  <CardHeader>
    <CardTitle>Group Values (values compression)</CardTitle>
  </CardHeader>
  <CardBody>
    <Form>
              <FormGroup>
                <Label for="exampleText">Raw Values</Label>
                <Input type="textarea" name="text" id="exampleText" rows="7" bind:value={rawValues}/>
              </FormGroup>
              <FormGroup>
                  <Label for="exampleEmail">Values delimiter</Label>
                  <Input
                        type="text"
                        name="delimiter"
                        id="delimiter"
                        placeholder="values delimiter (eg: ,)"
                        bind:value={delimiter} />
                </FormGroup>
            </Form>
            {#if !inProgress}
                <div>
                    <Button color="primary" block on:click={doPost}>CREATE GROUPS</Button>
                </div>
            {/if}
            {#if inProgress}
                <Button color="primary" block  on:click={doPost} disabled>PLEASE WAIT ....</Button>
            {/if}
  </CardBody>
</Card>
<br />
<hr />
{#if result}
<Card class="mb-3">
  <CardHeader>
    <CardTitle>Groups</CardTitle>
  </CardHeader>
  <CardBody>
  <Alert color="primary">
      <h5 class="alert-heading text-capitalize">Original number of terms: {result.originalInputSize}</h5>
      <h5 class="alert-heading text-capitalize">Reduced number of terms: {result.compressedSize}</h5>
      <h5 class="alert-heading text-capitalize">Reduction %: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{result.reductionPercentage}%</h5>
    </Alert>
    <Table hover bordered>
      <tbody>
      {#each result.entries as group, i}
        <tr>
          <th scope="row">Group-{i}</th>
          <td>{group}</td>
        </tr>
        {/each}
      </tbody>
    </Table>
  </CardBody>
</Card>
<hr />
<Card class="mb-3">
  <CardHeader>
    <CardTitle>Non Grouped Values</CardTitle>
  </CardHeader>
  <CardBody>
    <Table hover bordered>
      <tbody>
      {#each result.ungrouped as ungroup}
        <tr>
          <td>{ungroup}</td>
        </tr>
        {/each}
      </tbody>
    </Table>
  </CardBody>
</Card>
{/if}


